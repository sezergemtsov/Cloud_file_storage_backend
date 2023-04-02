package ru.netology.diplomafinal.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.diplomafinal.exceptions.FileProcessingError;
import ru.netology.diplomafinal.models.clientCustomResponces.NewFileNameTransfer;
import ru.netology.diplomafinal.models.clientCustomResponces.ViewElement;
import ru.netology.diplomafinal.models.storrage.FileBearer;
import ru.netology.diplomafinal.repository.FileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileServiceTest {

    @Mock
    FileRepository repository;
    @InjectMocks
    FileService fileService;

    @Test
    void getFile() {
        //arrange
        byte[] savedFile = {1, 2, 3};
        when(repository.findByFileNameEquals("file")).thenReturn(Optional.of(new FileBearer(1L, "file", 0L, savedFile)));
        //act
        byte[] file = fileService.getFile("file");
        //assert
        assertArrayEquals(savedFile, file);
    }

    @Test
    void getFiles() {
        //arrange
        List<ViewElement> respond = new ArrayList<>();
        respond.add(new ViewElement("first", 0L));
        respond.add(new ViewElement("second", 0L));
        respond.add(new ViewElement("third", 0L));
        List<FileBearer> request = new ArrayList<>();
        request.add(new FileBearer(1L, "first", 0L, null));
        request.add(new FileBearer(2L, "second", 0L, null));
        request.add(new FileBearer(3L, "third", 0L, null));
        request.add(new FileBearer(4L, "additional", 0L, null));
        when(repository.findAll()).thenReturn(request);
        //act
        List<ViewElement> result = fileService.getFiles(3);
        //assert
        assertThat(respond, is(result));
    }

    @Test
    void removeFile() {
        //arrange
        when(repository.findByFileNameEquals("file")).thenReturn(Optional.empty());
        //act-assert
        Assertions.assertThrows(FileProcessingError.class, () -> fileService.removeFile("file"));
    }

    @Test
    void updateFile() {
        //arrange
        NewFileNameTransfer transfer = new NewFileNameTransfer("newName");
        when(repository.findByFileNameEquals("file")).thenReturn(Optional.empty());
        //act-assert
        Assertions.assertThrows(FileProcessingError.class, () -> fileService.updateFile("file", transfer));
    }

}