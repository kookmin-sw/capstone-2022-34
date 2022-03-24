package com.capstone.momomeal.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity //이제부터 JPA가 관리하는 entity가된다
@Table(name = "members")
public class Members {


    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // -> IDENTITY는 DB에서 자동적으로 생성해주는거(하지만 나는 할필요없다)
    @NotNull
    @Id
    @Column(name = "userid", nullable = false)
    private String userid;

    @NotNull
    @Column(name = "pwd")
    private String pwd;

    @NotNull
    @Column(name = "username") //DB의 컬럼명이랑 같게 해야한다
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "homeaddress")
    private String homeaddress;

    @Column(name = "userrate")
    private Float userrate;

    @OneToMany(mappedBy = "member")
    private List<JoinedChatRoom> joinedChatRooms = new ArrayList<>();

    // Member에서 joinChatRoom 삭제
    public void deleteJoinChatRoomFromMember(JoinedChatRoom joinedChatRoom){
        joinedChatRooms.remove(joinedChatRoom);

    }

    public boolean belongsToMember(JoinedChatRoom joinedChatRoom){
        return joinedChatRooms.contains(joinedChatRoom);
    }

    public Members(){}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public Float getUserrate() {
        return userrate;
    }

    public void setUserrate(Float userrate) {
        this.userrate = userrate;
    }

    @Override
    public String toString() {
        return "Members{" +
                "userid='" + userid + '\'' +
                ", pwd='" + pwd + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", phonenumber='" + phonenumber + '\'' +
                ", homeaddress='" + homeaddress + '\'' +
                ", userrate=" + userrate +
                '}';
    }
}
