import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Home } from '../views/home.component';
import { RunVisualizer } from '../views/runvisualizer.component';
import { About } from '../views/about.component';
import { Settings } from '../views/settings.component';
import { User } from '../views/user.component';
import { NotFound } from '../views/notfound.component';

const routes: Routes = [
    { path: 'home', component: Home },
    { path: 'runvisualizer', component: RunVisualizer },
    { path: 'about', component: About },
    { path: 'settings', component: Settings },
    { path: 'user', component: User },
    { path: 'about', component: About },
    { path: '', redirectTo: 'home', pathMatch: 'full' },
    { path: '**', component: NotFound }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
