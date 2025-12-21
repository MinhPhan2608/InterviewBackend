package org.example.interviewbe.config.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.models.Account;
import org.example.interviewbe.repositories.AccountRepo;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailService implements UserDetailsService {
    AccountRepo repository;
    @Override
    @NonNull
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUsername(username);
        if (account == null){
            throw new EntityNotFoundException("User not found");
        }
        return new CustomUserDetail(account.getUsername(), account.getPassword());
    }
}
