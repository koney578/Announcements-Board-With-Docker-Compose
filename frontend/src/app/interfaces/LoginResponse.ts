import {UserResponse} from "./UserResponse";

export interface LoginResponse {
  token: string
  user: UserResponse
}
