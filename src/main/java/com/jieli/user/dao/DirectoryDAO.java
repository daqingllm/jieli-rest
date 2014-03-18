package com.jieli.user.dao;

import com.jieli.user.entity.Directory;
import com.jieli.user.entity.Friend;
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

    public Directory loadByUserId(String userId) {
        return col.findOne("{userId:#}", userId).as(Directory.class);
    }

    public Directory upsertFriend(String userId, Friend friend) {
        Directory directory = loadByUserId(userId);
        if (directory == null) {
            directory = new Directory();
            directory.userId = userId;
            addFriend(directory, friend);
            return save(directory);
        }

        Friend f = lookup(friend.userId, directory.content);
        if (f == null) {
            addFriend(directory, friend);
            return save(directory);
        }

        f.group = friend.group;
        f.special = friend.special;
        return save(directory);
    }

    private void addFriend(Directory directory, Friend friend) {
        if (directory.content == null) {
            directory.content = new ArrayList<Friend>();
        }
        directory.content.add(friend);
    }

    private Friend lookup(String userId, List<Friend> friends) {
        if (CollectionUtils.isEmpty(friends)) {
            return null;
        }
        for (Friend friend : friends) {
            if (friend.userId.equals(userId)) {
                return friend;
            }
        }
        return null;
    }

    public Directory deleteFriend(String userId, String friendId) {
        Directory directory = loadByUserId(userId);
        if (directory == null || directory.content == null) {
            return directory;
        }

        Friend friend = lookup(friendId, directory.content);
        if (friend == null) {
            return directory;
        }

        directory.content.remove(friend);
        return save(directory);
    }
}
