CAG Racing Management System Environment
========================================
Certificates from Let's encrypt are installed using.

```
sudo mkdir /opt/letsencrypt
sudo chown ubuntu:ubuntu letsencrypt
git clone https://github.com/letsencrypt/letsencrypt /opt/letsencrypt
sudo service nginx stop
/opt/letsencrypt/letsencrypt-auto certonly --standalone
sudo service nginx start
sudo openssl dhparam -out /etc/nginx/dhparams.pem 2048
```

Virtual host directive can be found under `etc/nginx/sites-available`

For auto renewal of certificate, place the files `opt/renew-certificates.sh` and `opt/le-droidrace.caglabs.se-webroot.ini`
in `/opt/`.

Run `renew-certificates.sh` as root in a cron job every third day or so to auto renew the cert when it's about to expire.

Example crontab entry:

```
2 15 */3 * * /opt/renew-certificates.sh
``` 


