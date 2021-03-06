package com.minimerce.core.domain.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gemini on 23/03/2017.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByApiKey(String apiKey);
}
