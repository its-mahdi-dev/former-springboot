package com.example.former.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.former.model.Field;
import com.example.former.model.Form;
import com.example.former.service.FormService;

import java.util.List;
@RestController
@RequestMapping("/forms")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping
    public List<Form> getAllForms() {
        return formService.getAllForms();
    }

    @PostMapping
    public Form createForm(@RequestBody Form form) {
        return formService.createForm(form);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(formService.getFormById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Form> updateForm(@PathVariable("id") Long id, @RequestBody Form formDetails) {
        return ResponseEntity.ok(formService.updateForm(id, formDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable("id") Long id) {
        formService.deleteForm(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/fields")
    public ResponseEntity<List<Field>> getFields(@PathVariable("id") Long id) {
        return ResponseEntity.ok(formService.getFieldsByFormId(id));
    }

    @PutMapping("/{id}/fields")
    public ResponseEntity<List<Field>> updateFields(@PathVariable("id") Long id, @RequestBody List<Field> fields) {
        return ResponseEntity.ok(formService.updateFields(id, fields));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<Form> togglePublishStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(formService.togglePublishStatus(id));
    }

    @GetMapping("/published")
    public List<Form> getPublishedForms() {
        return formService.getPublishedForms();
    }
}

