CAG Racing Management System Environment
========================================
Certificates from Let's encrypt are installed using.

```
cd /usr/local/sbin
sudo curl -O https://dl.eff.org/certbot-auto
sudo chmod a+x certbot-auto
sudo service nginx stop
sudo certbot-auto certonly --standalone
sudo openssl dhparam -out /etc/nginx/dhparams.pem 2048
sudo service nginx start
sudo mkdir -p /var/www/letsencrypt
sudo chown -R "$USER":www-data /var/www/letsencrypt
sudo chmod -R 0755 /var/www/letsencrypt
```

Virtual host directive can be found under `etc/nginx/sites-available`

For auto renewal of certificate, place the files `opt/renew-certificates.sh` and `opt/le-droidrace.caglabs.se-webroot.ini`
in `/opt/`.

Run `renew-certificates.sh` as root in a cron job every third day or so to auto renew the cert when it's about to expire.

Example crontab entry:

```
2 15 */3 * * /opt/renew-certificates.sh
``` 

Install racing manager as a service
===================================
Make sure the files under `/opt` are in place on the server

```bash
sudo ln -s /opt/racing/bin/cag-racing /etc/init.d/
sudo update-rc.d cag-racing defaults
```

