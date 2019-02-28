package com.indavara.contactsapi.util;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.Base64;
import java.util.List;

public class CommonUtils {

    public static void addInCriteria(Query query, List<String> countryCodes, String s) {
        if (isNotEmptyStrings(countryCodes)) {
            query.addCriteria(Criteria.where(s).in(countryCodes));
        }
    }

    public static boolean isNotEmptyStrings(List<String> stringList) {
        return !CollectionUtils.isEmpty(stringList);
    }

    public static final String getBasicAuthorizationToken(String userName, String password){
        String token = userName+":"+password;
        String bytes = Base64.getEncoder().encodeToString(token.getBytes());
        return bytes;
    }

    public static void main(String[] args) {
        System.out.println(getBasicAuthorizationToken("admin", "admin"));
    }


}
