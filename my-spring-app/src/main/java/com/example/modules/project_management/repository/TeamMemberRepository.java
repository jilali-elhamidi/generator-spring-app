package com.example.modules.project_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.project_management.model.TeamMember;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends BaseRepository<TeamMember, Long> {

}
