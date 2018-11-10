import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import {  FileUploader } from 'ng2-file-upload/ng2-file-upload';

import { Messages } from '../../../config/Messages';
import { Cosntants } from '../../../config/Constants';

@Component({
  selector: 'app-form-truck',
  templateUrl: './form-truck.component.html',
  styleUrls: ['./form-truck.component.scss']
})
export class FormTruckComponent implements OnInit {

  formTruck: FormGroup;
  message: string;

  constructor(private route : ActivatedRoute,
    private router : Router, 
    private formBuilder: FormBuilder,) { }

  public uploader: FileUploader = new FileUploader({url: Cosntants.ENDPOINT_API + Cosntants.SERVICE_TRUCKS_UP_LOAD, 
    itemAlias: 'file',
    headers: [{ name: 'email', value: '' }]});

  ngOnInit() {
    this.message = '';
    this.formTruck = this.getForm();

    this.uploader.onAfterAddingFile = (file) => { file.withCredentials = false; };
    this.uploader.onCompleteItem = (item: any, response: any, status: any, headers: any) => {
         if(status == 202){
           this.message = response;
         }else{
          let info = JSON.parse(response);
          this.message = info.message;
         }
         this.uploader.clearQueue();

     };
  }

  onSubmit(){
    
    if(this.controlTruck.controls.email.errors == null){
      this.uploader.options.headers[0].value = this.formTruck.value.email;
      this.uploader.uploadAll();
    }
  }

  get controlTruck(){
    return <FormGroup> this.formTruck;
  }

  private getForm() : FormGroup{
    return this.formBuilder.group({
      email : ['', [Validators.required, Validators.maxLength(100), Validators.email]]	        
    })
  }
  

  readFile(fileEvent: any){
    const file = fileEvent.target.files[0];
    const extFile = this.getExtFile(file.name);
        
    if (Cosntants.TYPE_FILE_VALID != extFile.toUpperCase()){
      this.message = Messages.FORM_CAMION_NO_TYPE_FILE;
      this.uploader.clearQueue();
    }else{
      this.message = "";      
    }
  }

  getExtFile (name: string) : string{
    var ext;
    const point = name.indexOf(".") ;    
    if(point > 0){
      ext = name.substring(point + 1, name.length);
    }
    return ext;
  }
}

