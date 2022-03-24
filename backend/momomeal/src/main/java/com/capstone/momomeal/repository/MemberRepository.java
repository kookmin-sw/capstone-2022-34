package com.capstone.momomeal.repository;

import com.capstone.momomeal.domain.Members;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Members save(Members member);
    Optional<Members> findById(String userId);
    Optional<Members> findByName(String name);
    List<Members> findAll();
    Optional<Members> findIdAndPwd(String userid, String pwd);
}
