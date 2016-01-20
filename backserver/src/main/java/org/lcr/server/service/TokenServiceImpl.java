package org.lcr.server.service;

import org.lcr.server.common.Message;
import org.lcr.server.dao.TokenDao;
import org.lcr.server.entity.BackTokenEntity;
import org.lcr.server.vo.ResponseVo;
import org.lcr.server.vo.TokenRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/1/13.
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenDao tokenDao;

    public ResponseVo getToken(TokenRequestVo requestVo) {
        BackTokenEntity vo = new BackTokenEntity();

        vo.setLoginName(requestVo.getLoginName());
        vo.setPassword(requestVo.getPassword());

        BackTokenEntity ret = tokenDao.getToken(vo);

        ResponseVo result = new ResponseVo();

        if (ret == null) {
            result.setCode(Message.USER_NOT_EXISTS_CODE);
            result.setMessage(Message.USER_NOT_EXISTS_MSG);
        } else {
            result.setCode(Message.SUCCESS_CODE);
            result.setMessage(Message.SUCCESS_MSG);
            result.setEntity(ret);
        }

        return result;
    }
}
