package ru.netology.diplomafinal.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplomafinal.exceptions.FileProcessingError;
import ru.netology.diplomafinal.models.clientCustomResponces.ViewElement;
import ru.netology.diplomafinal.models.clientCustomResponces.NewFileNameTransfer;
import ru.netology.diplomafinal.models.storrage.FileBearer;
import ru.netology.diplomafinal.repository.FileRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Transactional
    public byte[] getFile(String fileName) {
        Optional<FileBearer> file = fileRepository.findByFileNameEquals(fileName);
        if (file.isPresent()) {
            return file.get().getFileHolder();
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }

    @Transactional
    public List<ViewElement> getFiles(int limit) {
        List<FileBearer> files = fileRepository.findAll();
        List<ViewElement> respond = new ArrayList<>(limit);

        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                if (i == limit) {
                    break;
                }
                respond.add(new ViewElement(files.get(i).getFileName(), files.get(i).getSize()));
            }
        }
        return respond;
    }

    @Transactional
    public void removeFile(String filename) {
        Optional<FileBearer> file = fileRepository.findByFileNameEquals(filename);
        if (file.isPresent()) {
            fileRepository.deleteById(file.get().getId());
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }

    @Transactional
    public void updateFile(String filename, NewFileNameTransfer newName) {
        System.out.println(newName.name());
        Optional<FileBearer> file = fileRepository.findByFileNameEquals(filename);
        if (file.isPresent()) {
            fileRepository.updateFile(newName.name(), file.get().getId());
        } else {
            throw new FileProcessingError("Файл не найден");
        }
    }

    @Transactional
    public void addFile(MultipartFile file) {

        try {
            fileRepository.save(FileBearer.builder()
                    .fileName(file.getOriginalFilename())
                    .size(file.getSize())
                    .fileHolder(file.getInputStream().readAllBytes())
                    .build());
        } catch (IOException | RuntimeException e) {
            throw new FileProcessingError(e.getMessage());
        }
    }

}
