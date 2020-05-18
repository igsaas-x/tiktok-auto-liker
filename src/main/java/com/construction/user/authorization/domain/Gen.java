package com.construction.user.authorization.domain;

import java.util.List;

public class Gen {

    private static final List<String> entityNameList = List.of("project", "task", "house", "street", "boq", "payment");

    public static void main(String[] args) {
        var i = 10;
        for (String name : entityNameList) {
            for (ActionName actionName : ActionName.values()) {
                for (PermissionScope scope : PermissionScope.values())
                    System.out.println(String.format("insert into permission(id,version,entity_name,action_name,scope,code_name)" +
                                    " values(%s,0,'%s','%s','%s','%s')", i++, name.toUpperCase(), actionName.name(), scope.name(),
                            actionName + "_" + scope.name() + "_" + name.toUpperCase()));
            }
        }
    }
}
