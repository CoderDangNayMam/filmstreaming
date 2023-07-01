package com.vti.trangwebxemphimv2.utils;

import com.alibaba.fastjson.JSON;
import com.vti.trangwebxemphimv2.config.exception.AppExceptionDto;
import com.vti.trangwebxemphimv2.entity.dto.LoginDtoV2;
import com.vti.trangwebxemphimv2.entity.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class JWTTokenUtils {
   private static final long EXPIRATION_TIME = 864000000; // 10 days, thời hạn của token
   private static final String SECRET = "123456"; // Chữ ký bí mật
   private static final String PREFIX_TOKEN = "Bearer"; // Ký tự đầu của token
   private static final String AUTHORIZATION = "Authorization"; // Key của token trên header



   // Hàm này dùng để tạo ra token
   public String createAccessToken(LoginDtoV2 loginDtoV2) {
       // Tạo giá trị thời hạn token ( bằng thời gian hiện tại + 10 ngày hoặc tuỳ theo )
       Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
       String token = Jwts.builder()
               .setId(String.valueOf(loginDtoV2.getId())) //set giá trị Id
               .setSubject(loginDtoV2.getUsername()) // set giá trị subject
               .setIssuedAt(new Date())
               .setIssuer("VTI")
               .setExpiration(expirationDate) // set thời hạn của token
               .signWith(SignatureAlgorithm.HS512, SECRET) // khai báo phương thức mã hóa token và chữ ký bí mật
               .claim("authorities", loginDtoV2.getRole().name()) // thêm trường authorities để lưu giá trị phân quyền
               .claim("user-Agent", loginDtoV2.getUserAgent()) // thêm trường user-Agent để lưu thông tin trình duyệt đang dùng
               .compact(); // tạo ra mã token từ các thông tin trên


       // Tạo đối tượng token và lưu vào database
//       Token tokenEntity = new Token();
//       tokenEntity.setToken(token);
//       tokenEntity.setExpiration(expirationDate);
//       tokenEntity.setUserAgent(loginDto.getUserAgent());
//       tokenRepository.save(tokenEntity);
       return token;
   }


   // Hàm này dùng để giải mã hóa token
   public LoginDtoV2 parseAccessToken(String token) {
       LoginDtoV2 loginDtoV2 = new LoginDtoV2();
       if (!token.isEmpty()) {
           try {
               // bỏ Bearer = PREFIX_TOKEN
               token = token.replace(PREFIX_TOKEN, "").trim();
               Claims claims = Jwts.parser()
                       .setSigningKey(SECRET)
                       .parseClaimsJws(token).getBody();
               // Lấy ra các thông tin
               String user = claims.getSubject();
               Role role = Role.valueOf(claims.get("authorities").toString());
               String userAgent = claims.get("user-Agent").toString();
               // Gán các thông tin vào đối tượng LoginDto, có thể sử dụng constructor
               loginDtoV2.setUsername(user);
               loginDtoV2.setRole(role);
               loginDtoV2.setUserAgent(userAgent);
           } catch (Exception e) {
               log.error(e.getMessage());
               return null;
           }
       }
       return loginDtoV2;
   }


   public boolean checkToken(String token, HttpServletResponse response, HttpServletRequest httpServletRequest) {
       try {
           if (StringUtils.isBlank(token) || !token.startsWith(PREFIX_TOKEN)) { // token bị trống -> lỗi
               responseJson(response, new AppExceptionDto( 401, "Token ko hợp lệ", httpServletRequest.getRequestURI()));
               return false;
           }
           // Bỏ từ khoá Bearer ở token và trim 2 đầu
           token = token.replace(PREFIX_TOKEN, "").trim();

            // giải mã token
           LoginDtoV2 loginDtoV2 = parseAccessToken(token);
           if (loginDtoV2 == null) { // Ko có token trên hệ thống
               responseJson(response, new AppExceptionDto(401,"Token ko tồn tại hoặc hết hạn", httpServletRequest.getRequestURI()));
               return false;
           }
       } catch (Exception e) {
           responseJson(response, new AppExceptionDto(401,e.getMessage(), httpServletRequest.getRequestURI()));
           return false;
       }
       return true;
   }




   // Hàm này dùng để response dữ liệu khi gặp lỗi
   private void responseJson(HttpServletResponse response, AppExceptionDto appException){
       response.setCharacterEncoding("UTF-8");
       response.setContentType("application/json");
       response.setStatus(401);
       try {
           response.getWriter().print(JSON.toJSONString(appException));
       } catch (IOException e) {
           log.debug(e.getMessage());
           throw new RuntimeException(e);
       }
   }
}
