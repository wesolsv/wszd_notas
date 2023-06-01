import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./componentes/login/login.component";
import { CommonModule } from '@angular/common';

const routes: Routes = [
    {path:'', component:LoginComponent},
]

@NgModule({
    imports: [RouterModule.forRoot(routes), 
              CommonModule],
    exports: [RouterModule]
})

export class AppRoutingModule{}