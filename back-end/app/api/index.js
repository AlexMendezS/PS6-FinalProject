const {Router} = require('express');
const StudentRouter = require('./students');

const router = new Router();
router.get('/status', (req, res) => res.status(200).json('ok'));
router.use('/students', StudentRouter);

module.exports = router;
