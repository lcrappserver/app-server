package org.lcr.server.dao;


import org.lcr.server.vo.TokenRequestVo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Types;

@Repository
public class TokenDaoImpl implements TokenDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public boolean checkUserExits(String loginName, String password) {
        String sql = "select count(1) " +
                "from tu_app_user_info " +
                "where login_name = ? and password = ?";
        Integer count = jdbcTemplate.queryForObject(sql,
                new Object[] {loginName, password},
                new int[] {Types.VARCHAR, Types.VARCHAR}, Integer.class);
        if (count != 0)
            return true;
        else
            return false;
    }
}
