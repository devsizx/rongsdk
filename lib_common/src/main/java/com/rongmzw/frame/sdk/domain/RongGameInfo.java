package com.rongmzw.frame.sdk.domain;

/**
 * Created by Administrator on 2017/12/19.
 */

public class RongGameInfo {
    private String gameAreaId;
    private String gameArea;
    private String gameLevel;
    private String roleId;
    private String userRole;

    public String getGameAreaId() {
        return gameAreaId;
    }

    public void setGameAreaID(String gameAreaId) {
        this.gameAreaId = gameAreaId;
    }

    public String getGameArea() {
        return gameArea;
    }

    public void setGameArea(String gameArea) {
        this.gameArea = gameArea;
    }

    public String getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(String gameLevel) {
        this.gameLevel = gameLevel;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "RongGameInfo [gameAreaId=" + gameAreaId +
                ", gameArea=" + gameArea +
                ", gameLevel=" + gameLevel +
                ", roleId=" + roleId +
                ", userRole=" + userRole +
                "]";
    }
}
