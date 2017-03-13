/**
 * Copyright 2017 Confluent Inc.
 **/
package io.confluent.kql.parser.tree;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

public class Query
    extends Statement {

  private final Optional<With> with;
  private final QueryBody queryBody;
  private final List<SortItem> orderBy;
  private final Optional<String> limit;

  public Query(
      Optional<With> with,
      QueryBody queryBody,
      List<SortItem> orderBy,
      Optional<String> limit) {
    this(Optional.empty(), with, queryBody, orderBy, limit);
  }

  public Query(
      NodeLocation location,
      Optional<With> with,
      QueryBody queryBody,
      List<SortItem> orderBy,
      Optional<String> limit) {
    this(Optional.of(location), with, queryBody, orderBy, limit);
  }

  private Query(
      Optional<NodeLocation> location,
      Optional<With> with,
      QueryBody queryBody,
      List<SortItem> orderBy,
      Optional<String> limit) {
    super(location);
    requireNonNull(with, "with is null");
    requireNonNull(queryBody, "queryBody is null");
    requireNonNull(orderBy, "orderBy is null");
    requireNonNull(limit, "limit is null");

    this.with = with;
    this.queryBody = queryBody;
    this.orderBy = orderBy;
    this.limit = limit;
  }

  public Optional<With> getWith() {
    return with;
  }

  public QueryBody getQueryBody() {
    return queryBody;
  }

  public List<SortItem> getOrderBy() {
    return orderBy;
  }

  public Optional<String> getLimit() {
    return limit;
  }

  @Override
  public <R, C> R accept(AstVisitor<R, C> visitor, C context) {
    return visitor.visitQuery(this, context);
  }

  @Override
  public String toString() {
    return toStringHelper(this)
        .add("with", with.orElse(null))
        .add("queryBody", queryBody)
        .add("orderBy", orderBy)
        .add("limit", limit.orElse(null))
        .omitNullValues()
        .toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }
    Query o = (Query) obj;
    return Objects.equals(with, o.with) &&
           Objects.equals(queryBody, o.queryBody) &&
           Objects.equals(orderBy, o.orderBy) &&
           Objects.equals(limit, o.limit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(with, queryBody, orderBy, limit);
  }
}