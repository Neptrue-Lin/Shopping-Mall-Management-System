package org.neptrueworks.ordermanagement.data.reposition;

import org.neptrueworks.ordermanagement.data.entitizing.IDataEntitizable;


@Deprecated(forRemoval = true)
public interface IDataRepositable<TIdentifier extends Comparable<TIdentifier>, TEntity extends IDataEntitizable<TIdentifier>> {

//    protected IDataContextualisable context;
//    protected LinkedList<IRepositable> repositories;

//    Design Pattern: Visitor
//    Where GroupBy-Having OrderBy Join-On In Exists
//    Aggregate functions
//    Streamable<> Optional<>
//    Functional Programming StreamAPI

//    IDataConditionable<Entity> like(String , String condition);

//    IDataAggregatable<Entity> min(String , Comparable<> condition);
//    IDataAggregatable<Entity> max(String , Comparable<> condition);
//    IDataAggregatable<Entity> count(String , Comparable<> condition);
//    IDataAggregatable<Entity> sum(String , Comparable<> condition);
//    IDataAggregatable<Entity> abs(String , Comparable<> condition);
//    IDataAggregatable<Entity> average(String , Comparable<> condition);
//    IDataAggregatable<Entity> round(String , Comparable<> condition);
//    IDataAggregatable<Entity> ceil(String , Comparable<> condition);
//                              ...

//    IDataNumerable<Entity> plus(String , Comparable<> condition);
//    IDataNumerable<Entity> minus(String , Comparable<> condition);
//    IDataNumerable<Entity> multiply(String , Comparable<> condition);
//    IDataNumerable<Entity> divide(String , Comparable<> condition);
//    IDataNumerable<Entity> modulus(String , Comparable<> condition);

//    IDataComparable<Entity> equals(String , Comparable<> condition);
//    IDataComparable<Entity> greaterThan(String , Comparable<> condition);
//    IDataComparable<Entity> lessThan(String , Comparable<> condition);

//    IDataConditionable<Entity> isNull(String );
//    IDataConditionable<Entity> isNotNull(String );

//    IDataConditionable<Entity> in(String, IDataQueryable<Entity> );
//    IDataConditionable<Entity> exists(String, IDataQueryable<Entity> );

//    IDataLogicizable<Entity> and(String , Comparable<> condition);
//    IDataLogicizable<Entity> or(String , Comparable<> condition);
//    IDataLogicizable<Entity> not(String , Comparable<> condition);

//    IDataQueryable<Entity> where();

//    IDataGroupable<Entity> group(String);
//    IDataQueryable<Entity> having();

//    IDataQueryable<Entity> innerJoin(IDataQueryable<Entity>, Map<String, String> joints);
//    IDataQueryable<Entity> rightJoin(IDataQueryable<Entity>, Map<String, String> joints);
//    IDataQueryable<Entity> leftJoin(IDataQueryable<Entity>, Map<String, String> joints);
//    IDataQueryable<Entity> fullJoin(IDataQueryable<Entity>, Map<String, String> joints);

//    IDataQueryable<Entity> order(String, boolean isAscending);

//    IDataNavigable<Entity> seek(Map<String, Object> keyset);
//    IDataQueryable<Entity> forward(int count);
//    IDataQueryable<Entity> backward(int count);

//    IDataTakeable<Entity> skip(int offset);
//    IDataQueryable<Entity> take(int count);

//    IDataQueryable<Entity> union();
//    IDataQueryable<Entity> intersect();
//    IDataQueryable<Entity> except();

//    List<Entity> selectAll();
//    Entity selectScalar();
//    Entity selectScalar(TIdentifier id);
}
