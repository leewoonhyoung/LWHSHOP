package com.shop.repository;

import com.nimbusds.jose.crypto.opts.OptionUtils;
import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.hibernate.loader.Loader.SELECT;

public interface MemberRepository  extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    @Query("select m from Member m WHERE m.email= m.name ")
    Optional<Member> findByEmail2(String email);

    @Query("SELECT m FROM Member n order by p.id DESC ")
    List<Member> findAllDesc();




}
