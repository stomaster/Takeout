package com.qtc.ecommerce_demo.service.impl;

import com.qtc.ecommerce_demo.dto.UserProfileDTO;
import com.qtc.ecommerce_demo.dto.UserProfileVO;
import com.qtc.ecommerce_demo.entity.User;
import com.qtc.ecommerce_demo.entity.UserCollection;
import com.qtc.ecommerce_demo.entity.UserProfile;
import com.qtc.ecommerce_demo.mapper.UserMapper;
import com.qtc.ecommerce_demo.mapper.UserCollectionMapper;
import com.qtc.ecommerce_demo.mapper.UserProfileMapper;
import com.qtc.ecommerce_demo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;
    private final UserCollectionMapper userCollectionMapper;

    @Override
    public UserProfileDTO getUserProfile(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");//?
        }

        UserProfile profile = userProfileMapper.selectByUserId(userId);
        List<UserCollection> collections = userCollectionMapper.selectByUserId(userId);

        UserProfileDTO dto = new UserProfileDTO();
        dto.setUserId(userId);
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        if (profile != null) {
            dto.setAvatarUrl(profile.getAvatarUrl());
            dto.setNickname(profile.getNickname());
            dto.setSignature(profile.getSignature());
            dto.setViewCount(profile.getViewCount());
            dto.setCollectCount(profile.getCollectCount());
            dto.setPurchaseCount(profile.getPurchaseCount());
            dto.setGrade(profile.getGrade());
            dto.setSchool(profile.getSchool());
            dto.setCollege(profile.getCollege());
            dto.setStudentId(profile.getStudentId());
            dto.setTags(profile.getTags());
        }

        dto.setCollections(collections);
        return dto;
    }

    @Override
    @Transactional
    public boolean saveOrUpdateUserProfile(Long userId, UserProfileVO userProfileVO) {
        if (userMapper.selectById(userId) == null) {
            throw new RuntimeException("用户不存在");
        }

        UserProfile existing = userProfileMapper.selectByUserId(userId);

        if (existing == null) {
            UserProfile profile = new UserProfile();//现存为空，要新建一个
            profile.setUserId(userId);
            copyVOToProfile(userProfileVO, profile);
            return userProfileMapper.insert(profile) > 0;//插入，id+1
        } else {
            copyVOToProfile(userProfileVO, existing);
            return userProfileMapper.update(existing) > 0;//更新 id不变
        }
    }

    @Override
    public void incrementViewCount(Long userId) {
        userProfileMapper.incrementViewCount(userId);
    }

    @Override
    public List<UserCollection> getUserCollections(Long userId) {
        return userCollectionMapper.selectByUserId(userId);
    }

    @Override
    @Transactional
    public boolean addCollection(Long userId, Long productId, String productImage) {
        if (userCollectionMapper.exists(userId, productId) > 0) {
            return false;
        }

        UserCollection collection = new UserCollection();
        collection.setUserId(userId);
        collection.setProductId(productId);
        collection.setProductImage(productImage);
        int result = userCollectionMapper.insert(collection);

        if (result > 0) {
            userProfileMapper.incrementCollectCount(userId);
        }

        return result > 0;
    }

    @Override
    @Transactional  //需要数据库，要这个
    public boolean removeCollection(Long userId, Long productId) {
        int result = userCollectionMapper.delete(userId, productId);
        return result > 0;
    }

    private void copyVOToProfile(UserProfileVO vo, UserProfile profile) {
        profile.setAvatarUrl(vo.getAvatarUrl());
        profile.setNickname(vo.getNickname());
        profile.setSignature(vo.getSignature());
        profile.setGrade(vo.getGrade());
        profile.setSchool(vo.getSchool());
        profile.setCollege(vo.getCollege());
        profile.setStudentId(vo.getStudentId());
        profile.setTags(vo.getTags());
    }
}














