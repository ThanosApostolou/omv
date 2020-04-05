import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Index } from '../views/index.component';
import { About } from '../views/about.component';

const routes: Routes = [
    { path: 'index', component: Index },
    { path: 'about', component: About },
    { path: '', redirectTo: 'index', pathMatch: 'full' }//,
    //{ path: '**', component: PageNotFoundComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
