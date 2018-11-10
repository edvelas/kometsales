import {Routes, RouterModule} from '@angular/router';

import { DashboardComponent } from './module/dashboard/dashboard.component';
import { FormTruckComponent } from './module/truck/form-truck/form-truck.component';

const appRoutes: Routes = [
    { path : '', component : DashboardComponent },
    { path : 'truck', component : FormTruckComponent },
    { path : '**', redirectTo : '' }
];

export const routing = RouterModule.forRoot(appRoutes);