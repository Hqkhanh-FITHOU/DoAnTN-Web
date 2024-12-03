package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.UsersDTO;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    @Override
    public User createUser(UsersDTO usersDTO,  MultipartFile avatarFile) throws IOException {
        User user = new User();
        user.setUsername(usersDTO.getUsername());
        user.setPassword(usersDTO.getPassword());
        user.setFullname(usersDTO.getFullname());
        user.setEmail(usersDTO.getEmail());
        user.setPhone(usersDTO.getPhone());
        user.setRoles(usersDTO.getRoles());
        if (avatarFile != null && !avatarFile.isEmpty()) {
            // Tạo thư mục nếu chưa tồn tại
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Tạo tên file duy nhất
            String filename = System.currentTimeMillis() + "_" + avatarFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);

            try {
                // Ghi file vào hệ thống
                Files.write(filePath, avatarFile.getBytes());
                user.setAvatar(filename); // Lưu tên file vào đối tượng user
            } catch (IOException e) {
                // Xử lý lỗi ghi file
                throw new IOException("Could not save avatar file: " + e.getMessage(), e);
            }
        }
        return usersRepository.save(user);
    }

    @Override
    public boolean updateUser(Long id, UsersDTO usersDTO) throws IOException{
        Optional<User> optionalUser = usersRepository.findById(id);
        User user = optionalUser.get();

        user.setUsername(usersDTO.getUsername());
        user.setPassword(usersDTO.getPassword());
        user.setFullname(usersDTO.getFullname());
        user.setEmail(usersDTO.getEmail());
        user.setPhone(usersDTO.getPhone());
        user.setRoles(usersDTO.getRoles());

        if (usersDTO.getAvatar() != null && !usersDTO.getAvatar().isEmpty()) {
            // Sử dụng thư mục cố định để lưu ảnh
            String uploadDir = System.getProperty("user.dir") + "/uploads/avatars/";
            String fileName = System.currentTimeMillis() + "_" + usersDTO.getAvatar().getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            // Tạo thư mục nếu chưa tồn tại
            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath); // Tạo thư mục nếu cần
                }
            } catch (IOException e) {
                throw new RuntimeException("Không thể tạo thư mục tải lên: " + uploadPath, e);
            }

            // Đảm bảo đường dẫn file chính xác
            Path filePath = uploadPath.resolve(fileName);
            try {
                // Lưu file vào thư mục
                usersDTO.getAvatar().transferTo(filePath.toFile());
            } catch (IOException e) {
                throw new RuntimeException("Không thể lưu file: " + filePath, e);
            }

            // Lưu đường dẫn ảnh vào database
            user.setAvatar("uploads/avatars/" + fileName); // Đường dẫn tương đối để dễ hiển thị trên frontend
        }

        user.setUserPoint(0L);

        try {
            usersRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        User user = usersRepository.findById(id).get();
        if(user != null){
            /**
             * TODO: kiểm tra xem món ăn có trong chu kỳ tính doanh thu không nếu có thì không cho xóa
             */
            try {
                usersRepository.deleteById(id);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return usersRepository.findByPhone(phone);
    }

    @Override
    public User findByUserId(Long id) {
        return usersRepository.findById(id).get();
    }

    public boolean disableUser(Long userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Vô hiệu hóa tài khoản (set active = false)
            user.setActive(false);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean enableUser(Long userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Kích hoạt lại tài khoản (set active = true)
            user.setActive(true);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

}
