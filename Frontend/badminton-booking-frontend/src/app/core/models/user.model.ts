// Mirrors badminton_booking_backend.entity.User (password is never sent by the API).
export interface User {
  id: number;
  name: string;
  email: string;
  phone?: string;
  role?: string;
  profileImage?: string;
  points: number;
  createdAt: string;
}

// Mirrors badminton_booking_backend.dto.RegisterRequest
export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  phone?: string;
}

// Mirrors badminton_booking_backend.dto.LoginRequest
export interface LoginRequest {
  email: string;
  password: string;
}

// Mirrors badminton_booking_backend.dto.AuthResponse
export interface AuthResponse {
  id: number;
  name: string;
  email: string;
  phone?: string;
  points: number;
}

// Mirrors badminton_booking_backend.dto.UserUpdateRequest
export interface UserUpdateRequest {
  name?: string;
  phone?: string;
  profileImage?: string;
}
