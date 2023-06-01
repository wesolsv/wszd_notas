import { Component } from '@angular/core';
import { Login } from 'src/app/model/Login';
import { LoginService } from 'src/app/servicos/login.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  public email: string = "";
  public senha: string = "";
  public login = new Login;

  constructor(private service: LoginService) {
    this.login.email = this.email;
    this.login.senha = this.senha;
  }

  ngOnInit():void{}

  public realizarLogin(): void {
    this.service.realizarLogin(this.login).subscribe(
      (res:Login)=>{this.login = res},
      (err) => {alert("Erro ao realizar login")}
    )
  }
}
