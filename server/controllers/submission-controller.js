var connection = require('../config/config');
const base = {};

base.submit = async function (req, res) {
  var subm = {
    assunto: req.body.assunto,
    lat: req.body.lat,
    lng: req.body.lng,
    obs: req.body.obs,
    img: 'teste',
    id_user: req.body.id_user,
    data: req.body.data
  }
  connection.query('INSERT INTO submissions SET ?', subm, function (error, results, fields) {
    if (error) {
      res.json({
        status: false,
        message: 'There are some error with query'
      })
    } else {
      res.json({
        status: true,
        data: results,
        message: 'Submission sucessfully'
      })
    }
  });
}

base.getAll = async function (req, res) {
  connection.query('SELECT id_submissions, assunto,lat,lng,obs,id_user,data FROM  submissions', function (error, results, fields) {
    if (error) {
      res.json({
        status: false,
        message: 'There are some error with query'
      })
    } else {
      res.json({
        status: true,
        data: results,
        message: 'GetAll successful'
      })
    }
  });
}


base.getById = async function (req, res) {
  var id = {
    id_user: req.params.id_user
  }
  connection.query('SELECT id_submissions,assunto,lat,lng,obs,id_user,data FROM  submissions WHERE id_user = ?', [id.id_user], function (error, results, fields) {
    if (error) {
      res.json({
        status: false,
        message: 'There are some error with query'
      })
    } else {
      res.json({
        status: true,
        data: results,
        message: 'GetById successful'
      })
    }
  });
}

base.delete = async function (req, res) {
  var id = {
    id_submissions: req.params.id_submissions
  }
  connection.query('DELETE FROM  submissions WHERE id_submissions = ?', [id.id_submissions], function (error, results, fields) {
    if (error) {
      res.json({
        status: false,
        message: 'There are some error with query'
      })
    } else {
      res.json({
        status: true,
        data: results,
        message: 'Delete successful'
      })
    }
  });
}

base.updates = async function (req, res) {
  
  var data = [req.body.assunto,req.body.obs,'teste', req.body.data, req.body.id_submissions]
  connection.query('UPDATE submissions SET assunto=?, obs=?, img=?, data=? WHERE id_submissions=?', data, function (error, results, fields) {
    if (error) {
      res.json({
        status: false,
        message: 'There are some error with query'
      })
    } else {
      res.json({
        status: true,
        data: results,
        message: 'Update sucessfully'
      })
    }
  });
}

module.exports = base;