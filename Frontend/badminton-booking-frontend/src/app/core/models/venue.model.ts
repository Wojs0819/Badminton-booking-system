// Mirrors badminton_booking_backend.entity.Venue
export interface Venue {
  id: number;
  name: string;
  address: string;
  description?: string;
  openingTime: string;
  closingTime: string;
  pricePerHour: number;
  imageUrl?: string;
  location?: string;
}
