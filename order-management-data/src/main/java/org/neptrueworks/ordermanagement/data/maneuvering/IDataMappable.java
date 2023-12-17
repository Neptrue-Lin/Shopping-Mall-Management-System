package org.neptrueworks.ordermanagement.data.maneuvering;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

public interface IDataMappable {
    void setSQLSession(SqlSession sqlSession);
}
