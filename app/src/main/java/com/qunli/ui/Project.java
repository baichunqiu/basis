package com.qunli.ui;

/**
 * 作业列表的模板实体
 */
public class Project {


    /**
     * project_id : 5195745c90f811ea87960242ac120003
     * name : 16
     * desc : 16
     * status : enable
     * created_at : 2020-05-08 14:51:18
     * creater : {"user_id":"07112cac7eee11eaa0080242ac120003","bloc_id":"05241a407eee11ea8cff0242ac120003","username":"admin","realname":"admin","role":3,"status":"enable","login_at":"0000-00-00 00:00:00","last_time_login_at":"0000-00-00 00:00:00"}
     */

    private String project_id;
    private String name;
    private String desc;
    private String status;
    private String created_at;
    private CreaterBean creater;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public CreaterBean getCreater() {
        return creater;
    }

    public void setCreater(CreaterBean creater) {
        this.creater = creater;
    }

    public static class CreaterBean {
        /**
         * user_id : 07112cac7eee11eaa0080242ac120003
         * bloc_id : 05241a407eee11ea8cff0242ac120003
         * username : admin
         * realname : admin
         * role : 3
         * status : enable
         * login_at : 0000-00-00 00:00:00
         * last_time_login_at : 0000-00-00 00:00:00
         */

        private String user_id;
        private String bloc_id;
        private String username;
        private String realname;
        private int role;
        private String status;
        private String login_at;
        private String last_time_login_at;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBloc_id() {
            return bloc_id;
        }

        public void setBloc_id(String bloc_id) {
            this.bloc_id = bloc_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLogin_at() {
            return login_at;
        }

        public void setLogin_at(String login_at) {
            this.login_at = login_at;
        }

        public String getLast_time_login_at() {
            return last_time_login_at;
        }

        public void setLast_time_login_at(String last_time_login_at) {
            this.last_time_login_at = last_time_login_at;
        }
    }
}
