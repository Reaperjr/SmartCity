const express = require('express');
const SubmController = require('../controllers/submission-controller');
const router = express.Router();

// routes
router.post('/submit',SubmController.submit);
router.get('/', SubmController.getAll);
router.get('/:id_user', SubmController.getById);
router.delete('/:id_submissions', SubmController.delete);
router.put('/updates', SubmController.updates);




module.exports = router;