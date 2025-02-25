import {AnnouncementUserResponse} from "./AnnouncementUserResponse";
import {CategoryResponse} from "./CategoryResponse";

export interface AnnouncementResponse {
  id: number
  title: string
  description: string
  createdAt: Date
  endsAt: Date
  isReviewed: boolean
  category: CategoryResponse
  user: AnnouncementUserResponse
}
