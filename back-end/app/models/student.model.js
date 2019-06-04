const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

module.exports = new BaseModel('Student', {
  studentNumber: Joi.number()
    .integer(),
  queue: Joi.number()
    .integer(),
  name: Joi.string(),
  firstName: Joi.string(),
  status: Joi.string(),
  notif: Joi.string(),
  mail: Joi.string(),
  password: Joi.string(),
  birthday: Joi.string(),
  gender: Joi.string(),
  nationality: Joi.string(),
  address: Joi.string(),
  city: Joi.string(),
  phone: Joi.string(),
  educationStream: Joi.string(),
});
