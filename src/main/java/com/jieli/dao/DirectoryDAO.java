package com.jieli.dao;

import com.jieli.entity.user.Directory;
import com.jieli.entity.user.Friend;
import com.jieli.mongo.GenericDAO;
import com.jieli.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liming_liu
 * Date: 14-3-16
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryDAO extends GenericDAO<Directory> {

    public Directory loadByUserId(int userId) {
        return col.findOne("{userId:#}", userId).as(Directory.class);
    }

    public Directory upsertFriend(int userId, Friend friend) {
        Directory directory = loadByUserId(userId);
        if (directory == null) {
            directory = new Directory();
            directory.userId = userId;
            addFriend(directory, friend);
            return save(directory);
        } else {
            Friend f = lookup(friend.userId, directory.content);
            if (f == null) {
                addFriend(directory, friend);
                return save(directory);
            } else {
                f.group = friend.group;
                f.special = friend.special;
                return save(directory);
            }
        }
    }

    private void addFriend(Directory directory, Friend friend) {
        if (directory.content == null) {
            directory.content = new ArrayList<Friend>();
        }
        directory.content.add(friend);
    }

    private Friend lookup(int userId, List<Friend> friends) {
        if (CollectionUtils.isEmpty(friends)) {
            return null;
        }
        for (Friend friend : friends) {
            if (friend.userId == userId) {
                return friend;
            }
        }
        return null;
    }

    public Directory deleteFriend(int userId, int friendId) {
        Directory directory = loadByUserId(userId);
        if (directory == null || directory.content == null) {
            return directory;
        } else {
            Friend friend = lookup(friendId, directory.content);
            if (friend == null) {
                return directory;
            } else {
                directory.content.remove(friend);
                return save(directory);
            }
        }
    }
}
