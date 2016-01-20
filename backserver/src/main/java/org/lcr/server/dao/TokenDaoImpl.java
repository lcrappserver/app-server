package org.lcr.server.dao;

import org.lcr.server.entity.BackTokenEntity;
import org.lcr.server.entity.BackUserInfoEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class TokenDaoImpl implements TokenDao {

  @Resource
  private JdbcTemplate jdbcTemplate;

  public BackTokenEntity getToken(BackTokenEntity vo) {

    String sql = "select login_name, password, access_token, user_id " + "from tu_back_user_token "
        + "where login_name = ? and password = ?";
    List<BackTokenEntity> list =
        jdbcTemplate.query(sql, new Object[] {vo.getLoginName(), vo.getPassword()},
            new int[] {Types.VARCHAR, Types.VARCHAR}, new RowMapper<BackTokenEntity>() {

              public BackTokenEntity mapRow(ResultSet rs, int index) throws SQLException {
                BackTokenEntity entity = new BackTokenEntity();
                entity.setAccessToken(rs.getString("access_token"));
                entity.setLoginName(rs.getString("login_name"));
                entity.setPassword(rs.getString("password"));
                entity.setUserId(rs.getString("user_id"));
                return entity;
              }
            });

    if (list != null && list.size() > 0)
      return list.get(0);
    else
      return null;
  }

  public BackUserInfoEntity getUserInfo(BackTokenEntity vo) {
    String sql = "select t1.login_name, t1.password, t1.access_token, "
        + "t2.user_id, t2.email, t2.mobile, t2.real_name, t2.icon, t2.sex, t2.degree "
        + "from tu_back_user_token t1 "
        + "inner join tu_back_user_info t2 on t1.user_id = t2.user_id "
        + "where t1.login_name = ? and t1.password = ?";
    List<BackUserInfoEntity> list =
        jdbcTemplate.query(sql, new Object[] {vo.getLoginName(), vo.getPassword()},
            new int[] {Types.VARCHAR, Types.VARCHAR}, new RowMapper<BackUserInfoEntity>() {
              public BackUserInfoEntity mapRow(ResultSet rs, int index) throws SQLException {
                BackUserInfoEntity entity = new BackUserInfoEntity();
                entity.setUserId(rs.getString("user_id"));
                entity.setEmail(rs.getString("email"));
                entity.setMobile(rs.getString("mobile"));
                entity.setRealName(rs.getString("real_name"));
                entity.setIcon(rs.getString("icon"));
                entity.setSex(rs.getString("sex"));
                entity.setDegree(rs.getString("degree"));
                BackTokenEntity token = new BackTokenEntity();
                token.setAccessToken(rs.getString("access_token"));
                token.setLoginName(rs.getString("login_name"));
                token.setPassword(rs.getString("password"));
                token.setUserId(rs.getString("user_id"));
                entity.setBackTokenEntity(token);
                return entity;
              }
            });

    if (list != null && list.size() > 0)
      return list.get(0);
    else
      return null;
  }
}
