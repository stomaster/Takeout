package com.qtc.ecommerce_demo.service;

import com.qtc.ecommerce_demo.dto.UserProfileDTO;
import com.qtc.ecommerce_demo.dto.UserProfileVO;
import com.qtc.ecommerce_demo.entity.UserCollection;
import java.util.List;

public interface UserProfileService {
    UserProfileDTO getUserProfile(Long userId);
    boolean saveOrUpdateUserProfile(Long userId, UserProfileVO userProfileVO);
    void incrementViewCount(Long userId);
    List<UserCollection> getUserCollections(Long userId);//????
    boolean addCollection(Long userId, Long productId, String productImage);
    boolean removeCollection(Long userId, Long productId);
}
