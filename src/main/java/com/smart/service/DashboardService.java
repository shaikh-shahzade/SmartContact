package com.smart.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.User;

public interface DashboardService {

	public String viewContacts(Model model, Principal principal, int page);

	public String addContacts(Model model, Principal principal);

	public String addContact(Contact contact, Model model, Principal principal, MultipartFile mfile,
			String description);

	public String updateContact(Integer id, Model model, Principal principal);

	public String updateValueContact(Contact contact, Model model, Principal principal, MultipartFile mfile, Integer id,
			String description);
}
