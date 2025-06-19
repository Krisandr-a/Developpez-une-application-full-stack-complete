package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<ThemeDto> getAllThemes() {
        return themeRepository.findAll()
                .stream()
                .map(theme -> new ThemeDto(
                        theme.getTitle(),
                        theme.getDescription()
                ))
                .collect(Collectors.toList());
    }

    public Theme addTheme(ThemeDto dto) {
        // Check for duplicate by title
        if (themeRepository.findByTitle(dto.getTitle()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Theme with this title already exists.");
        }

        // Convert DTO to entity
        Theme theme = new Theme();
        theme.setTitle(dto.getTitle());
        theme.setDescription(dto.getDescription());
        return themeRepository.save(theme);
    }

}

