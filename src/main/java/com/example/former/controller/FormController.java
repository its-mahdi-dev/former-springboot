package com.example.former.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.former.model.Field;
import com.example.former.model.Form;
import com.example.former.service.FormService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forms")
public class FormController {

    @Autowired
    private FormService formService;

    @GetMapping
    public List<Form> getAllForms() {
        return formService.getAllForms();
    }

    @PostMapping
    public Form createForm(@RequestBody Form form) {
        return formService.createForm(form);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable(name = "id") Long id) {
        Optional<Form> form = formService.getFormById(id);
        return form.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Form> updateForm(@PathVariable(name = "id") Long id, @RequestBody Form formDetails) {
        Optional<Form> formOptional = formService.getFormById(id);

        if (!formOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Form form = formOptional.get();
        form.setName(formDetails.getName());
        form.setPublishStatus(formDetails.isPublishStatus());
        form.setMethod(formDetails.getMethod());
        form.setSubmitUrl(formDetails.getSubmitUrl());

        formService.save(form); 

        return ResponseEntity.ok(form);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable(name = "id") Long id) {
        return formService.deleteForm(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/fields")
    public List<Field> getFields(@PathVariable(name = "id") Long id) {
        return formService.getFieldsByFormId(id);
    }

    @PutMapping("/{id}/fields")
    public ResponseEntity<List<Field>> updateFields(@PathVariable(name = "id") Long id, @RequestBody List<Field> fields) {
        Optional<Form> formOptional = formService.getFormById(id);
    
        if (!formOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
    
        Form form = formOptional.get();
        List<Field> updatedFields = form.getFields();
    
        for (Field field : fields) {
            Optional<Field> fieldToUpdate = updatedFields.stream()
                    .filter(existingField -> existingField.getId().equals(field.getId()))
                    .findFirst();
    
            if (fieldToUpdate.isPresent()) {
                Field existingField = fieldToUpdate.get();
                existingField.setName(field.getName());
                existingField.setLabel(field.getLabel());
                existingField.setType(field.getType());
                existingField.setDefaultValue(field.getDefaultValue());
            }
        }
    
        formService.save(form);
    
        return ResponseEntity.ok(form.getFields());
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<Form> togglePublishStatus(@PathVariable(name = "id") Long id) {
        Form form = formService.togglePublishStatus(id);
        return form != null ? ResponseEntity.ok(form) : ResponseEntity.notFound().build();
    }

    @GetMapping("/published")
    public List<Form> getPublishedForms() {
        return formService.getPublishedForms();
    }
}
