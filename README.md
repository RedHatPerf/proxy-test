# Proxy Test

This is a basic testsuite to benchmark analyze performance of proxies, [Envoy](https://www.envoyproxy.io) in particular.

## Running the test

The harness uses Ansible for provisioning. Most configurable settings are in the `hosts` file in root directory.

In order to start load driver ([SailRocket](https://github.com/RedHatPerf/SailRocket/)), proxy (Envoy) and backend (simple Vert.x HTTP server) run:

```bash
ansible-playbook -i hosts setup.yml
```

This does not trigger the load yet; benchmark is deployed and started with

```bash
ansible-playbook -i hosts test.yml
```

Top stop all the services run:

```bash
ansible-playbook -i hosts setup.yml --tags shutdown
```