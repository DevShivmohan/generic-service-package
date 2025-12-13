package com.generic.service.service.impl;

import com.generic.service.dto.GenericPaginationRes;
import com.generic.service.dto.SearchFilter;
import com.generic.service.dto.SearchFilterCriteria;
import com.generic.service.entity.GenericEntity;
import com.generic.service.exception.GenericException;
import com.generic.service.mapper.GenericMapper;
import com.generic.service.repository.GenericRepository;
import com.generic.service.service.RequestContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public abstract class GenericService<T_REQ, T_RES, T_ENTITY extends GenericEntity> {

    private final GenericRepository<T_ENTITY> repository;

    private final Class<T_RES> tResClass;

    private final Class<T_ENTITY> tEntityClass;

    private final RequestContext requestContext;

    @Transactional
    public T_RES update(T_REQ updateReq, UUID id) {
        getInternal(id);
        final T_ENTITY tEntity = GenericMapper.map(updateReq, tEntityClass);
        tEntity.setUuid(id);
        return GenericMapper.map(repository.saveAndFlush(tEntity), tResClass);
    }

    @Transactional
    public T_RES create(T_REQ createReq) {
        return GenericMapper.map(repository.saveAndFlush(GenericMapper.map(createReq, tEntityClass)), tResClass);
    }

    @Transactional
    public List<T_RES> create(List<T_REQ> createReqList) {
        return GenericMapper.mapList(repository.saveAllAndFlush(GenericMapper.mapList(createReqList, tEntityClass)), tResClass);
    }

    @Transactional
    public T_RES deleteSoft(UUID id) {
        final T_ENTITY dbEntity = getInternal(id);
        dbEntity.setDeleted(true);
        return GenericMapper.map(repository.saveAndFlush(dbEntity), tResClass);
    }

    @Transactional
    public T_RES deleteHard(UUID id) {
        final T_ENTITY dbEntity = getInternal(id);
        repository.delete(dbEntity);
        return GenericMapper.map(dbEntity, tResClass);
    }

    public GenericPaginationRes<T_RES> getAllPage(Pageable pageable) {
        final Page<T_ENTITY> tEntityPage = repository.findByDeletedFalse(pageable);
        return GenericPaginationRes.<T_RES>builder().totalPages(tEntityPage.getTotalPages()).totalElements(tEntityPage.getNumberOfElements()).pageSize(tEntityPage.getSize()).pageNumber(tEntityPage.getNumber()).lastPage(tEntityPage.isLast()).content(tEntityPage.getContent().stream().map(tEntity -> GenericMapper.map(tEntity, tResClass)).toList()).build();
    }

    public GenericPaginationRes<T_RES> search(SearchFilter searchFilter, Pageable pageable) {
        final Page<T_ENTITY> tEntityPage = repository.findAll(buildSpecification(searchFilter.getSearchCriteria()), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), searchFilter.getSortBy() != null && searchFilter.getSortOrder() != null ? Sort.by(Sort.Direction.fromString(searchFilter.getSortOrder()), searchFilter.getSortBy()) : Sort.unsorted()));
        return GenericPaginationRes.<T_RES>builder().totalPages(tEntityPage.getTotalPages()).totalElements(tEntityPage.getNumberOfElements()).pageSize(tEntityPage.getSize()).pageNumber(tEntityPage.getNumber()).lastPage(tEntityPage.isLast()).content(tEntityPage.getContent().stream().map(tEntity -> GenericMapper.map(tEntity, tResClass)).toList()).build();
    }

    public T_RES getById(UUID id) {
        return GenericMapper.map(repository.findByUuidAndDeletedFalse(id).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND.value(), "Record not found with id " + id)), tResClass);
    }

    public Optional<T_ENTITY> getByField(String fieldName, Object value) {
        return repository.findByField(fieldName, value);
    }

    public List<T_ENTITY> getAllByField(String fieldName, Object value) {
        return repository.findAllByField(fieldName, value);
    }

    private T_ENTITY getInternal(UUID id) {
        return repository.findByUuidAndDeletedFalse(id).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND.value(), "Record not found with id " + id));
    }

    private Specification<T_ENTITY> buildSpecification(List<SearchFilterCriteria> filters) {
        return (root, query, cb) -> {
            if (filters == null || filters.isEmpty()) {
                return cb.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isFalse(root.get("deleted")));
            for (SearchFilterCriteria filter : filters) {
                if (!filter.isValid()) continue;
                Path<?> path = resolvePath(root, filter.getFilterKey());
                switch (filter.getOperation()) {
                    case EQ -> predicates.add(cb.equal(path, filter.getValues().get(0)));
                    case NE -> predicates.add(cb.notEqual(path, filter.getValues().get(0)));
                    case GT -> predicates.add(greaterThan(cb, path, filter));
                    case LT -> predicates.add(lessThan(cb, path, filter));
                    case GTE -> predicates.add(greaterThanOrEqual(cb, path, filter));
                    case LTE -> predicates.add(lessThanOrEqual(cb, path, filter));
                    case LIKE -> predicates.add(
                            cb.like(
                                    cb.lower(path.as(String.class)),
                                    "%" + filter.getValues().get(0).toString().toLowerCase() + "%"
                            )
                    );
                    case IN -> predicates.add(path.in(filter.getValues()));
                    case BETWEEN -> predicates.add(between(cb, path, filter));
                    case IS_NULL -> predicates.add(cb.isNull(path));
                    case IS_NOT_NULL -> predicates.add(cb.isNotNull(path));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate greaterThan(CriteriaBuilder cb, Path<?> path, SearchFilterCriteria f) {
        return cb.greaterThan((Path<Comparable>) path, (Comparable) f.getValues().get(0));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate lessThan(CriteriaBuilder cb, Path<?> path, SearchFilterCriteria f) {
        return cb.lessThan((Path<Comparable>) path, (Comparable) f.getValues().get(0));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate greaterThanOrEqual(CriteriaBuilder cb, Path<?> path, SearchFilterCriteria f) {
        return cb.greaterThanOrEqualTo((Path<Comparable>) path, (Comparable) f.getValues().get(0));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate lessThanOrEqual(CriteriaBuilder cb, Path<?> path, SearchFilterCriteria f) {
        return cb.lessThanOrEqualTo((Path<Comparable>) path, (Comparable) f.getValues().get(0));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate between(CriteriaBuilder cb, Path<?> path, SearchFilterCriteria f) {
        return cb.between(
                (Path<Comparable>) path,
                (Comparable) f.getValues().get(0),
                (Comparable) f.getValues().get(1)
        );
    }

    private Path<?> resolvePath(From<?, ?> root, String key) {
        if (!key.contains(".")) {
            return root.get(key);
        }
        String[] parts = key.split("\\.");
        Path<?> path = root;
        for (String part : parts) {
            path = path.get(part);
        }
        return path;
    }
}
