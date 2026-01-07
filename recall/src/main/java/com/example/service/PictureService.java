package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import com.example.models.Picture;
import com.example.repository.PictureRepository;

@Service
public class PictureService {
    final PictureRepository picturesRepository;

    public PictureService(PictureRepository picturesRepository) {
        this.picturesRepository = picturesRepository;
    }

    public List<Picture> getAllPictures() {
        return picturesRepository.findAll();
    }

    public Picture getPictureById(Long id) {
        return picturesRepository.findById(id).orElse(null);
    }

    public List<Picture> getPicturesByHashtag(String hashtag) {
        List<Picture> result = new ArrayList<>();
        List<Picture> pictures = picturesRepository.findAll();
        for (Picture pic : pictures) {
            if (pic.getHashtags().contains(hashtag)) {
                result.add(pic);
            }
        }
        return result;

    }

    public void deletePictureIfOwner(Long id, String username) {
        final Optional<Picture> pictureOpt = picturesRepository.findById(id);
        if (pictureOpt.isEmpty()) {
            throw new RuntimeException("Picture not found");
        }
        final Picture picture = pictureOpt.get();
        if (username == null || picture.getCreatedBy() == null || !username.equals(picture.getCreatedBy())) {
            throw new AccessDeniedException("You can only delete your own pictures");
        }
        picturesRepository.deleteById(id);
    }

}
