package com.qunli.ui;

import java.io.Serializable;
import java.util.List;

/**
 * 作业详情视图
 */
public class WorkDetails implements Serializable {

    /**
     * task_id : e92b050a94ec11ea90af0242ac120003
     * status : start
     * live_status : stop
     * record_status : stop
     * created_at : 2020-05-13 15:39:43
     * finish_at :
     * creater : {"user_id":"07112cac7eee11eaa0080242ac120003","bloc_id":"05241a407eee11ea8cff0242ac120003","username":"admin","realname":"admin","role":3,"status":"enable","login_at":"0000-00-00 00:00:00","last_time_login_at":"0000-00-00 00:00:00"}
     * story : [{"task_story_id":"101b67388f7811eaa9a40242ac120006","message":"1 = 1","pic":"","created_at":"2020-05-06 17:00:41"}]
     * video : [{"task_video_id":"171bcafc903411eaaac40242ac120006","type":"local","video_id":"sdadsadsadsa","video_url":"1232132132132","cover_url":"32132132132132","start_at":"2020-05-07 15:23:34","duration":"0:5:33","created_at":"2020-05-07 15:26:38"}]
     * parent_project : {"project_id":"5195745c90f811ea87960242ac120003","name":"16","desc":"16","status":"enable","created_at":"2020-05-08 14:51:18"}
     * room : {"room_id":1589162755,"stream_id":"e92b06fe94ec11ea940d0242ac120003","live_url":{"rtmp":"rtmp://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003","flv":"http://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003.flv","m3u8":"http://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003.m3u8","udp":"webrtc://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003"}}
     */

    private String task_id;
    private String status;
    private String live_status;
    private String record_status;
    private String start_at;
    private String finish_at;
    private CreaterBean creater;
    private ParentProjectBean parent_project;
    private RoomBean room;
    private List<StoryBean> story;
    private List<VideoBean> video;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLive_status() {
        return live_status;
    }

    public void setLive_status(String live_status) {
        this.live_status = live_status;
    }

    public String getRecord_status() {
        return record_status;
    }

    public void setRecord_status(String record_status) {
        this.record_status = record_status;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getFinish_at() {
        return finish_at;
    }

    public void setFinish_at(String finish_at) {
        this.finish_at = finish_at;
    }

    public CreaterBean getCreater() {
        return creater;
    }

    public void setCreater(CreaterBean creater) {
        this.creater = creater;
    }

    public ParentProjectBean getParent_project() {
        return parent_project;
    }

    public void setParent_project(ParentProjectBean parent_project) {
        this.parent_project = parent_project;
    }

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public List<StoryBean> getStory() {
        return story;
    }

    public void setStory(List<StoryBean> story) {
        this.story = story;
    }

    public List<VideoBean> getVideo() {
        return video;
    }

    public void setVideo(List<VideoBean> video) {
        this.video = video;
    }

    public static class CreaterBean implements Serializable {
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

    public static class ParentProjectBean implements Serializable {
        /**
         * project_id : 5195745c90f811ea87960242ac120003
         * name : 16
         * desc : 16
         * status : enable
         * created_at : 2020-05-08 14:51:18
         */

        private String project_id;
        private String name;
        private String desc;
        private String status;
        private String created_at;

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
    }

    public static class RoomBean implements Serializable {
        /**
         * room_id : 1589162755
         * stream_id : e92b06fe94ec11ea940d0242ac120003
         * live_url : {"rtmp":"rtmp://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003","flv":"http://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003.flv","m3u8":"http://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003.m3u8","udp":"webrtc://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003"}
         */

        private int room_id;
        private String stream_id;
        private LiveUrlBean live_url;

        public int getRoom_id() {
            return room_id;
        }

        public void setRoom_id(int room_id) {
            this.room_id = room_id;
        }

        public String getStream_id() {
            return stream_id;
        }

        public void setStream_id(String stream_id) {
            this.stream_id = stream_id;
        }

        public LiveUrlBean getLive_url() {
            return live_url;
        }

        public void setLive_url(LiveUrlBean live_url) {
            this.live_url = live_url;
        }

        public static class LiveUrlBean {
            /**
             * rtmp : rtmp://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003
             * flv : http://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003.flv
             * m3u8 : http://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003.m3u8
             * udp : webrtc://play.qunlivideo.com/live/e92b06fe94ec11ea940d0242ac120003
             */

            private String rtmp;
            private String flv;
            private String m3u8;
            private String udp;

            public String getRtmp() {
                return rtmp;
            }

            public void setRtmp(String rtmp) {
                this.rtmp = rtmp;
            }

            public String getFlv() {
                return flv;
            }

            public void setFlv(String flv) {
                this.flv = flv;
            }

            public String getM3u8() {
                return m3u8;
            }

            public void setM3u8(String m3u8) {
                this.m3u8 = m3u8;
            }

            public String getUdp() {
                return udp;
            }

            public void setUdp(String udp) {
                this.udp = udp;
            }
        }
    }

    public static class StoryBean implements Serializable {
        /**
         * task_story_id : 101b67388f7811eaa9a40242ac120006
         * message : 1 = 1
         * pic :
         * created_at : 2020-05-06 17:00:41
         */

        private String task_story_id;
        private String message;
        private String pic;
        private String created_at;

        public String getTask_story_id() {
            return task_story_id;
        }

        public void setTask_story_id(String task_story_id) {
            this.task_story_id = task_story_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }

    public static class VideoBean implements Serializable {
        /**
         * task_video_id : 171bcafc903411eaaac40242ac120006
         * type : local
         * video_id : sdadsadsadsa
         * video_url : 1232132132132
         * cover_url : 32132132132132
         * start_at : 2020-05-07 15:23:34
         * duration : 0:5:33
         * created_at : 2020-05-07 15:26:38
         */

        private String task_video_id;
        private String type;
        private String video_id;
        private String video_url;
        private String cover_url;
        private String start_at;
        private String duration;
        private String created_at;

        public String getTask_video_id() {
            return task_video_id;
        }

        public void setTask_video_id(String task_video_id) {
            this.task_video_id = task_video_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
