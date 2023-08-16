package jp.co.fukuya_k.db.generator;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AuthUserDetail extends MAuthDetail {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_auth_user.USER_ID
     *
     * @mbggenerated Fri Mar 25 21:00:44 JST 2016
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_auth_user.PASSWORD
     *
     * @mbggenerated Fri Mar 25 21:00:44 JST 2016
     */
    private String password;
}