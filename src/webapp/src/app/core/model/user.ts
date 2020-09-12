import {Role} from "src/app/core/model/role";

export class User {
  id: number;
  username: string;
  roles: Role;
  token: string;
}
