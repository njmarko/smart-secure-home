import { EventEmitter, Injectable } from '@angular/core';
import { AuthResponse } from 'src/app/model/AuthResponse';

@Injectable({
  providedIn: 'root',
})
export class CurrentUserService {
  private userKey: string = 'currentUser';
  currentUserChanged: EventEmitter<void> = new EventEmitter<void>();

  setCurrentUser(user: AuthResponse) {
    sessionStorage.setItem(this.userKey, JSON.stringify(user));
    this.currentUserChanged.emit();
  }

  onCurrentUserChanged(): EventEmitter<void> {
    return this.currentUserChanged;
  }

  removeCurrentUser(): void {
    sessionStorage.removeItem(this.userKey);
    this.currentUserChanged.emit();
  }

  hasUser(): boolean {
    return sessionStorage.getItem(this.userKey) !== null;
  }

  update(updated: any): void {
    let user = this.getCurrentUser();
    if (user == null) {
      return;
    }
    user = {
      ...user,
      name: updated.name,
      surname: updated.surname,
      email: updated.email,
    };
    sessionStorage.setItem(this.userKey, JSON.stringify(user));
    this.currentUserChanged.emit();
  }

  hasAuthority(authority: string): boolean {
    const user = this.getCurrentUser();
    if (!user) {
      return false;
    }
    return user.authorities.includes(authority);
  }

  getCurrentUser(): AuthResponse | null {
    const user: string | null = sessionStorage.getItem(this.userKey);
    if (!user) {
      return null;
    }
    return JSON.parse(user);
  }
}
