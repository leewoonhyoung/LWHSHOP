package com.shop.repository;

import com.nimbusds.jose.crypto.opts.OptionUtils;
import com.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

    Optional<Member> OptionalFindByEmail(String email);


}
