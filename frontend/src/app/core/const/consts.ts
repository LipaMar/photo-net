import {environment} from "../../../environments/environment";

export const routes = {
  home: "/home",
  register: "/register",
  discover: "/discover",
  followed: "/followed",
  profile: "/profile",
  myProfile: "/my-profile",
  instagram: "#",
  facebook: "#",
  twitter: "#"
};

export const endpoints = {
  discover: `${environment.apiUrl}/discover`,
  login: `${environment.apiUrl}/login`,
  register: `${environment.apiUrl}/register`,
  profile: `${environment.apiUrl}/profiles`,
  myProfile: `${environment.apiUrl}/my-profile`,
  categories: `${environment.apiUrl}/dictionary/categories`,
  follow: `${environment.apiUrl}/follow`,
  comment: `${environment.apiUrl}/profiles/comment`,
};
