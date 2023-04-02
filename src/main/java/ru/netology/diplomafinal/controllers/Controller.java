package ru.netology.diplomafinal.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.diplomafinal.models.authentification.LoginData;
import ru.netology.diplomafinal.models.authentification.LoginRespond;
import ru.netology.diplomafinal.models.clientCustomResponces.NewFileNameTransfer;
import ru.netology.diplomafinal.models.clientCustomResponces.ViewElement;
import ru.netology.diplomafinal.services.FileService;
import ru.netology.diplomafinal.services.TokenService;

import java.util.List;


@RestController
@RequestMapping("/")
public class Controller {

    public final AuthenticationManager authenticationManager;
    public final FileService fileService;
    public final TokenService tokenService;

    public Controller(AuthenticationManager authenticationManager, FileService fileService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.fileService = fileService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public LoginRespond login(@RequestBody LoginData loginData) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginData.login(),
                loginData.password()));
        return new LoginRespond(tokenService.generateToken(authentication));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/list")
    public List<ViewElement> getList(@RequestParam("limit") int limit) {
        return fileService.getFiles(limit);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/file")
    public void fileUpload(MultipartFile file) {
        fileService.addFile(file);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/file")
    public void fileRemove(@RequestParam("filename") String filename) {
        fileService.removeFile(filename);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam("filename") String filename) {
        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(fileService.getFile(filename));
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/file")
    public void updateFile(@RequestParam("filename") String filename, @RequestBody NewFileNameTransfer name) {
        fileService.updateFile(filename, name);
    }

}
