import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Footer } from './components/footer/footer';
import { Header } from './components/header/header';
import { Nav } from './components/nav/nav';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,Footer,Header,Nav],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('client');
}
